package test.console;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import vn.vnu.hus.mim.can.CAN;
import vn.vnu.hus.mim.can.CANException;
import vn.vnu.hus.mim.can.CANNode;
import vn.vnu.hus.mim.can.Hash;

public class Main {
	public static final String	HASH_FUNCTION	= "SHA-1";
	public static final int		KEY_LENGTH		= 160;
	public static final int		NUMBER_OF_NODE	= 100;

	public static void main(String[] args) {
		int numberOfNodes = NUMBER_OF_NODE;
		try {
			String host = InetAddress.getLocalHost().getHostAddress();
			int port = 9000;

			Hash.setFunction(HASH_FUNCTION);
			Hash.setKeyLength(KEY_LENGTH);

			CAN can = new CAN();

			// Create nodes
			for (int i = 0; i < numberOfNodes; i++) {

				URL url = new URL("http", host, port + i, "");
				try {

					can.createNode(url.toString());
				} catch (CANException e) {

					e.printStackTrace();
					System.exit(0);
				}
			}

			// Add to hyper rectangle
			for (int i = 1; i < numberOfNodes; i++) {
				CANNode node = can.getNode(i);
				node.join(can.getNode(0));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	}
}
