package knn;
import java.io.IOException;

import knn.client.Client;

/**
 * Classe main del Client.
 */
public class Main {

	/**
	* Punto di partenza dell'applicazione lato client.
	* 
	* @param args argomenti passati da terminale (non vengono gestiti)
	*/
	public static void main(String[] args) {
		String address = "localhost";
		int port = 2025;
		
		try {
			new Client(address, port);
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.toString());
			return;
		}
	}
}
